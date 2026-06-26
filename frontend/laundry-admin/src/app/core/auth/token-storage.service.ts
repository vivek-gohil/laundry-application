import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  private readonly ACCESS_TOKEN = 'access_token';
  private readonly TOKEN_TYPE = 'token_type';
  private readonly USERNAME = 'username';
  private readonly FULL_NAME = 'full_name';
  private readonly ROLES = 'roles';

  saveUser(data: any): void {

    localStorage.setItem(this.ACCESS_TOKEN, data.accessToken);
    localStorage.setItem(this.TOKEN_TYPE, data.tokenType);
    localStorage.setItem(this.USERNAME, data.username);
    localStorage.setItem(this.FULL_NAME, data.fullName);
    localStorage.setItem(this.ROLES, JSON.stringify(data.roles));

  }

  getAccessToken(): string | null {

    return localStorage.getItem(this.ACCESS_TOKEN);

  }

  getTokenType(): string {

    return localStorage.getItem(this.TOKEN_TYPE) ?? 'Bearer';

  }

  getUsername(): string | null {

    return localStorage.getItem(this.USERNAME);

  }

  getFullName(): string | null {

    return localStorage.getItem(this.FULL_NAME);

  }

  getRoles(): string[] {

    return JSON.parse(localStorage.getItem(this.ROLES) ?? '[]');

  }

  isLoggedIn(): boolean {

    return this.getAccessToken() !== null;

  }

  logout(): void {

    localStorage.clear();

  }

}