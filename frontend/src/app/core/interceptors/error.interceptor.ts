import {Inject, Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {BehaviorSubject, EMPTY, Observable, throwError} from 'rxjs';
import {catchError, filter, switchMap, take} from "rxjs/operators";
import {AuthService} from "../services/auth/auth.service";
import {TokensInfo} from "../models/tokensInfo";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  private isRefreshing = false;
  private accessTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);


  constructor(private authService: AuthService,
              @Inject('BASE_API_URL') private baseUrl: string) {
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(request)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          console.log(error)
          if (error.status === 401) {
            return this.handle401Error(request, next);
          } else {
            return throwError(error);
          }
        })
      )
  }

  private handle401Error(request: HttpRequest<any>, next: HttpHandler) {

    if (!this.isRefreshing) {
      this.isRefreshing = true;
      this.accessTokenSubject.next(null);

      return this.authService.refreshToken().pipe(
        switchMap((token: TokensInfo) => {
          this.isRefreshing = false;
          this.accessTokenSubject.next(token.accessToken);
          return next.handle(this.getApiReq(request, token)).pipe(
            catchError(() => {
              this.authService.logout();
              throw EMPTY;
            })
          );
        }));

    } else {
      return this.accessTokenSubject.pipe(
        filter(token => token != null),
        take(1),
        switchMap(jwt => {
          return next.handle(this.getApiReq(request, jwt));
        }));
    }
  }

  private getApiReq(request: HttpRequest<any>, token: TokensInfo) {
    const apiReq = request.clone({url: `${this.baseUrl}${request.url}`});
    return this.authService.addToken(apiReq, token.accessToken);
  }
}
