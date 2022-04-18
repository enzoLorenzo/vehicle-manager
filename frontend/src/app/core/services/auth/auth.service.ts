import {Injectable} from '@angular/core';
import {BehaviorSubject, EMPTY, Observable, of} from "rxjs";
import {HttpClient, HttpRequest} from "@angular/common/http";
import {catchError, mapTo, tap} from "rxjs/operators";
import {Router} from "@angular/router";
import {TokensInfo} from "../../models/tokensInfo";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly ACCESS_TOKEN = 'ACCESS_TOKEN';
  private readonly REFRESH_TOKEN = 'REFRESH_TOKEN';
  private readonly USER_ID = 'USER_ID';

  private _isLoggedIn$ = new BehaviorSubject<boolean>(false);
  isLoggedIn$ = this._isLoggedIn$.asObservable();

  private userType: UserType = UserType.CLIENT;


  constructor(private http: HttpClient,
              private router: Router) {
    // const token = localStorage.getItem(this.ACCESS_TOKEN);
    // this._isLoggedIn$.next(!!token);
  }

  login(username: string, password: string): Observable<boolean> {
    return this.http.post<TokensInfo>('/login', {username, password}).pipe(
      tap((tokensInfo: TokensInfo) => this.initLoginData(tokensInfo)),
      mapTo(true),
      catchError(error => {
        return of(false);
      })
    );
  }

  logout() {
    this.removeTokens();
    this.router.navigate(['/login']);
    return this.http.post<any>('/logout', {});

  }

  addToken(request: HttpRequest<any>, token: string): HttpRequest<any> {
    return request.clone({
      setHeaders: {
        'Authorization': `Bearer ${token}`
      }
    });
  }

  refreshToken(): Observable<TokensInfo> {
    return this.http.post<any>('/user/refresh-token', {
      'refreshToken': this.getRefreshToken()
    }).pipe(tap((tokens: TokensInfo) => {
        this.storeTokens(tokens.accessToken, tokens.refreshToken);
      }),
      catchError(() => {
        this.logout();
        throw EMPTY;
      }));
  }

  isLoggedIn(): boolean {
    return !!this.getAccessToken();
  }

  getAccessToken(): string | null {
    return localStorage.getItem(this.ACCESS_TOKEN);
  }

  getUserId(): string {
    return localStorage.getItem(this.USER_ID)!;
  }

  private getRefreshToken(): string | null {
    return localStorage.getItem(this.REFRESH_TOKEN);
  }

  private initLoginData(tokensInfo: TokensInfo): void {
    this.storeTokens(tokensInfo.accessToken, tokensInfo.refreshToken);
    localStorage.setItem(this.USER_ID, tokensInfo.userId);
    const payloadObject: TokenPayload = JSON.parse(atob(tokensInfo.accessToken.split('.')[1]));
    this.userType = payloadObject.userType;
  }

  private storeTokens(accessToken: string, refreshToken: string) {
    localStorage.setItem(this.ACCESS_TOKEN, accessToken);
    localStorage.setItem(this.REFRESH_TOKEN, refreshToken);
  }

  private removeTokens(): void {
    localStorage.removeItem(this.ACCESS_TOKEN);
    localStorage.removeItem(this.REFRESH_TOKEN);
    localStorage.removeItem(this.USER_ID);
  }

}

export interface TokenPayload {
  sub: string;
  roles: string[];
  iss: string;
  userType: UserType;
  exp: number;
}

export enum UserType {
  CLIENT = "CLIENT",
  DEALER = "DEALER",
}
