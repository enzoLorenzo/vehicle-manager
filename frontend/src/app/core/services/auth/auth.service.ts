import {Injectable} from '@angular/core';
import {BehaviorSubject, EMPTY, Observable, of} from "rxjs";
import {HttpClient, HttpRequest} from "@angular/common/http";
import {catchError, map, tap} from "rxjs/operators";
import {Router} from "@angular/router";
import {TokensInfo} from "../../models/tokensInfo";


export interface RegisterCommand {
  username: string;
  password: string;
  nickname: string;
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

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public isRefreshing = false;
  public accessTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);
  private readonly ACCESS_TOKEN = 'ACCESS_TOKEN';
  private readonly REFRESH_TOKEN = 'REFRESH_TOKEN';
  private readonly USER_ID = 'USER_ID';
  private readonly USER_TYPE = 'USER_TYPE';
  private _isLoggedIn$ = new BehaviorSubject<boolean>(false);
  isLoggedIn$ = this._isLoggedIn$.asObservable();
  private userType: UserType = UserType.CLIENT;

  constructor(private http: HttpClient,
              private router: Router) {
    // const token = localStorage.getItem(this.ACCESS_TOKEN);
    // this._isLoggedIn$.next(!!token);
  }

  register(command: RegisterCommand, userType: UserType): Observable<void> {
    return this.http.post<void>(this.getUserTypeUrl(userType), command);
  }

  login(username: string, password: string): Observable<boolean> {
    return this.http.post<TokensInfo>('/login', {username, password}).pipe(
      tap((tokensInfo: TokensInfo) => this.initLoginData(tokensInfo)),
      map(() => true),
      catchError(() => {
        return of(false);
      })
    );
  }

  logout() {
    this.http.post<any>(this.getUserTypeUrl() + '/logout', {}).subscribe();
    this.removeTokens();
    this.router.navigate(['/login']);
  }

  addToken(request: HttpRequest<any>, token: string): HttpRequest<any> {
    return request.clone({
      setHeaders: {
        'Authorization': `Bearer ${token}`
      }
    });
  }

  refreshToken(): Observable<TokensInfo> {
    return this.http.post<any>(this.getUserTypeUrl() + '/refresh-token', {
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
    if (this.isRefreshing) {
      return localStorage.getItem(this.REFRESH_TOKEN);
    }
    return localStorage.getItem(this.ACCESS_TOKEN);
  }

  getUserType(): UserType {
    return UserType[localStorage.getItem(this.USER_TYPE) as keyof typeof UserType];
  }

  private getUserTypeUrl(userType?: UserType) {
    if (!!userType) {
      return userType === UserType.CLIENT ? "/client" : "/dealer"
    }
    return this.userType === UserType.CLIENT ? "/client" : "/dealer";
  }

  private getRefreshToken(): string | null {
    return localStorage.getItem(this.REFRESH_TOKEN);
  }

  private initLoginData(tokensInfo: TokensInfo): void {
    this.storeTokens(tokensInfo.accessToken, tokensInfo.refreshToken);
    localStorage.setItem(this.USER_ID, tokensInfo.userId);
    const payloadObject: TokenPayload = JSON.parse(atob(tokensInfo.accessToken.split('.')[1]));
    localStorage.setItem(this.USER_TYPE, payloadObject.userType);
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
