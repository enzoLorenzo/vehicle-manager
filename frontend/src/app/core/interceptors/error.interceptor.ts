import {Inject, Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {BehaviorSubject, EMPTY, Observable, throwError} from 'rxjs';
import {catchError, filter, switchMap, take} from "rxjs/operators";
import {AuthService} from "../services/auth/auth.service";
import {TokensInfo} from "../models/tokensInfo";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

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

    if (!this.authService.isRefreshing) {
      this.authService.isRefreshing = true;
      this.authService.accessTokenSubject.next(null);

      return this.authService.refreshToken().pipe(
        switchMap((token: TokensInfo) => {
          this.authService.isRefreshing = false;
          this.authService.accessTokenSubject.next(token.accessToken);
          return next.handle(this.getApiReq(request, token)).pipe(
            catchError(() => {
              this.authService.isRefreshing = false;
              this.authService.logout();
              throw EMPTY;
            })
          );
        }),);

    } else {
      return this.authService.accessTokenSubject.pipe(
        filter(token => token != null),
        switchMap(jwt => {
          return next.handle(this.getApiReq(request, jwt));
        }));
    }
  }

  private getApiReq(request: HttpRequest<any>, token: TokensInfo) {
    request = this.authService.addToken(request, token.accessToken);
    return request.clone({url: `${request.url}`});
  }
}
