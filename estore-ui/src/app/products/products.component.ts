import { Component, Input, OnInit } from '@angular/core';
import { Product } from '../product';
import { ProductService } from '../product.service';
import { ActivatedRoute} from '@angular/router';

import { Observable, of } from 'rxjs';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  products: Product[] = [];
  @Input() deletable?: boolean;

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute) { }

  getProducts(): void {
    this.productService.getProducts()
      .subscribe(products => this.products = products);
  }

  getProductUrl(id: Number): String {
    const name = String(this.route.snapshot.paramMap.get('name'))
    return this.deletable ? `/products/admin/edit/${id}` : `/products/${id}/${name}`;
  }

  checkIfAdmin(): boolean{
    if(String(location.toString()).indexOf("admin") != -1) {return true}
    else {return false}
  }

  add(name: string, description: string, copies: string, price: string): void {
    name = name.trim();
    if (!name) { return; }
    this.productService.addProduct({ name: name, description: description, copies: +copies, price: +price } as Product)
      .subscribe(product => {
        this.products.push(product);
      });
  }

  delete(product: Product): void {
    this.products = this.products.filter(p => p !== product);
    this.productService.deleteProduct(product.id).subscribe();
  }

  ngOnInit(): void {
    this.getProducts();
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
