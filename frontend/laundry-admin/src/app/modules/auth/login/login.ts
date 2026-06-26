import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import {
  NonNullableFormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { Router } from '@angular/router';

import { MATERIAL_IMPORTS } from '../../../shared/material/material-imports';

import { AuthService } from '../../../core/auth/auth.service';
import { TokenStorageService } from '../../../core/auth/token-storage.service';

import { LoginRequest } from '../../../core/auth/models/login-request.model';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MATERIAL_IMPORTS
  ],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login {

  private readonly fb = inject(NonNullableFormBuilder);

  private readonly authService = inject(AuthService);

  private readonly tokenStorage = inject(TokenStorageService);

  private readonly router = inject(Router);

  hidePassword = true;

  loginForm = this.fb.group({

    username: ['', Validators.required],

    password: ['', Validators.required],

    rememberMe: [false]

  });

  onLogin(): void {

    if (this.loginForm.invalid) {

      this.loginForm.markAllAsTouched();

      return;

    }

    const request: LoginRequest = {

      username: this.loginForm.controls.username.value,

      password: this.loginForm.controls.password.value

    };

    this.authService.login(request).subscribe({

      next: (response) => {

        console.log('Login Success', response);

        this.tokenStorage.saveUser(response.data);

        this.router.navigate(['/dashboard']);

      },

      error: (error) => {

        console.error(error);

        alert('Invalid Username or Password');

      }

    });

  }

}