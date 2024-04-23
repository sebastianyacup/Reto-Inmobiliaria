import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { FileHandel } from '../model/file-handel.model';
import { Product } from '../model/product.model';
import { ProductService } from '../services/product/product.service';

@Component({
  selector: 'app-add-new-product',
  templateUrl: './add-new-product.component.html',
  styleUrls: ['./add-new-product.component.css']
})
export class AddNewProductComponent implements OnInit {
  isNewProduct = true;
  product: Product = {
    productId: null,
    productName: "",
    productDescription: "",
    productDiscountedPrice: 0,
    productActualPrice: 0,
    productImages:[],
  }

  constructor(private productService: ProductService, 
    private sanitizer: DomSanitizer,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.product = this.activatedRoute.snapshot.data['product'];

    if(this.product && this.product.productId){
      this.isNewProduct=false;
    }

  }

  addProduct(productForm: NgForm){
    const formattedProduct = {
        ...this.product,
        productDiscountedPrice: parseFloat(this.product.productDiscountedPrice.toString().replace(/\./g, '')),
        productActualPrice: parseFloat(this.product.productActualPrice.toString().replace(/\./g, ''))
    };

    const productFormData = this.prepareFormData(formattedProduct);
    
    this.productService.addProduct(productFormData).subscribe(
        (response: Product)=>{
            productForm.reset();
            this.product.productImages = [];
        },
        (error: HttpErrorResponse)=>{
            console.log(error);
        }
    );
}

  formatPrice(event: any) {
    const value = event.target.value.replace(/\D/g, '');
    const formattedValue = value.replace(/\B(?=(\d{3})+(?!\d))/g, '.');
    event.target.value = formattedValue;
}

  prepareFormData(product: Product): FormData {
    const formData = new FormData();

    formData.append(
      'product',
      new Blob([JSON.stringify(product)], {type: 'application/json'})
    );

    for(var i=0; i<product.productImages.length; i++){
      formData.append(
        'imageFile',
        product.productImages[i].file,
        product.productImages[i].file.name
      );
    }

    return formData;
  } 

  onFileSelected(event: any){
    if(event.target.files){
      const file= event.target.files[0];
      const fileHandel: FileHandel ={
        file: file,
        url: this.sanitizer.bypassSecurityTrustUrl(
          window.URL.createObjectURL(file)
        ),
      };
      this.product.productImages.push(fileHandel);
    }
  }

  removeImages(i: number){
    this.product.productImages.splice(i,1);
  }

  fileDropped(fileHandel : FileHandel) {
    this.product.productImages.push(fileHandel);
  }

}