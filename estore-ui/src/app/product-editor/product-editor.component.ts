import { Component, OnInit, Input } from '@angular/core';
import { Product } from '../product';

import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { ProductService } from '../product.service';

@Component({
  selector: 'app-product-editor',
  templateUrl: './product-editor.component.html',
  styleUrls: ['./product-editor.component.css']
})
export class ProductEditorComponent implements OnInit {
  @Input() product?: Product;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.getProduct();
  }
  
  getProduct(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.productService.getProduct(id)
      .subscribe(product => this.product = product);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    if (this.product) {
      this.productService.updateProduct(this.product)
        .subscribe(() => this.goBack());
    }
  }

}