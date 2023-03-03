import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { User } from './user';

import { catchError, tap } from 'rxjs/operators';

import { Observable, of } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private usersURL = 'http://localhost:8080/users';  // URL to USER web api
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.usersURL)
      .pipe(
        catchError(this.handleError<User[]>('getUsers', []))
      );
  }

  getUser(name: string): Observable<User> {
    const url = `${this.usersURL}/${name}`;
    return this.http.get<User>(url).pipe(
      catchError(this.handleError<User>(`getUser name=${name}`))
    );
  }

  createUser(name: string): Observable<User> {
    return this.http.post<User>(this.usersURL, name, this.httpOptions).pipe(
      catchError(this.handleError<User>('createUser'))
    );
  }

  updateUser(user: User): Observable<User> {
    return this.http.put<User>(this.usersURL, user, this.httpOptions).pipe(
      catchError(this.handleError<User>('updateUser'))
    )
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
   private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
