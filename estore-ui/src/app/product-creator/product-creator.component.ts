import { Component, OnInit } from '@angular/core';
import { Product } from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-product-creator',
  templateUrl: './product-creator.component.html',
  styleUrls: ['./product-creator.component.css']
})
export class ProductCreatorComponent implements OnInit {
  
  constructor(private productService: ProductService) { }

  add(name: string, description: string, copies: string, price: string): void {
    name = name.trim();
    if (!name) { return; }
    this.productService.addProduct({ name: name, description: description, copies: +copies, price: +price } as Product).subscribe();
  }

  ngOnInit(): void {

  }

}