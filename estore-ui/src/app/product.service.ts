import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Product } from './product';

import { catchError, tap } from 'rxjs/operators';

import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private productsURL = 'http://localhost:8080/products';  // URL to PRODUCT web api
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.productsURL)
      .pipe(
        catchError(this.handleError<Product[]>('getProducts', []))
      );
  }

  getProduct(id: number): Observable<Product> {
    const url = `${this.productsURL}/${id}`;
    return this.http.get<Product>(url).pipe(
      catchError(this.handleError<Product>(`getProduct id=${id}`))
    );
  }

  updateProduct(product: Product): Observable<any> {
    return this.http.put(this.productsURL, product, this.httpOptions).pipe(
      catchError(this.handleError<any>('updateProduct'))
    );
  }

  addProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(this.productsURL, product, this.httpOptions).pipe(
      catchError(this.handleError<Product>('addProduct'))
    );
  }

  deleteProduct(id: number): Observable<Product> {
    const url = `${this.productsURL}/${id}`;
  
    return this.http.delete<Product>(url, this.httpOptions).pipe(
      catchError(this.handleError<Product>('deleteProduct'))
    );
  }

  searchProducts(term: string): Observable<Product[]> {
    if (!term.trim()) {
      return of([]);
    }
    return this.http.get<Product[]>(`${this.productsURL}/?name=${term}`).pipe(
      catchError(this.handleError<Product[]>('searchHeroes', []))
    );
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
