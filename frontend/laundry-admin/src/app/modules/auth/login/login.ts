import { CommonModule } from '@angular/common';
import {
  Component,
  DestroyRef,
  inject,
  signal
} from '@angular/core';

import {
  NonNullableFormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

import { Router } from '@angular/router';

import { finalize } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

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

  private readonly destroyRef = inject(DestroyRef);

  readonly hidePassword = signal(true);

  readonly isLoading = signal(false);

  readonly loginForm = this.fb.group({

    username: [
      '',
      [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(50)
      ]
    ],

    password: [
      '',
      [
        Validators.required,
        Validators.minLength(6)
      ]
    ],

    rememberMe: [false]

  });

  togglePasswordVisibility(): void {

    this.hidePassword.update(value => !value);

  }

  onLogin(): void {

    if (this.loginForm.invalid) {

      this.loginForm.markAllAsTouched();

      return;

    }

    this.isLoading.set(true);

    const request: LoginRequest = {

      username: this.loginForm.controls.username.getRawValue(),

      password: this.loginForm.controls.password.getRawValue()

    };

    this.authService.login(request)

      .pipe(

        finalize(() => this.isLoading.set(false)),

        takeUntilDestroyed(this.destroyRef)

      )

      .subscribe({

        next: (response) => {

          // Save complete user
          this.tokenStorage.saveUser(response.data);

          // If your service has saveToken(), uncomment below
          // this.tokenStorage.saveToken(response.data.accessToken);

          this.router.navigate(['/dashboard']);

        },

        error: (error) => {

          console.error(error);

          // TODO:
          // Replace with MatSnackBar in Part 4

        }

      });

  }

}