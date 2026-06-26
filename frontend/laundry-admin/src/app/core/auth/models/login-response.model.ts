export interface LoginResponse {
    accessToken: string;
    tokenType: string;
    expiresIn: number;
    username: string;
    fullName: string;
    roles: string[];
}