import { Component, OnInit, Input } from '@angular/core';
import { Product } from '../product';

import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';

import { ProductService } from '../product.service';
import { CartService } from '../cart.service';
import { delay } from 'rxjs';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {
  @Input() product?: Product;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private cartService: CartService,
    private location: Location,
    private router: Router
  ) {this.router.routeReuseStrategy.shouldReuseRoute = () => false;}

  ngOnInit(): void {
    this.getProduct();
  }
  
  getProduct(): void { 
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.productService.getProduct(id)
      .subscribe(product => this.product = product);
  }

  goBack(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.location.back();
  }

  addToCart(product: Product): void{
    const username = String(this.route.snapshot.paramMap.get('name'))
    if(product.copies > 0) {
      this.cartService.addToCart(username, product).subscribe()
      product.copies = product.copies - 1;
      this.productService.updateProduct(product).subscribe()
      //location.reload()
    } else {
      console.error("no copies available")
    }
  }
}
