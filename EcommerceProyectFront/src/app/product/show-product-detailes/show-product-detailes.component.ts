import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { ImageProcessingService } from '../../services/image/image-processing.service';
import { ShowProductImagesDialogComponent } from '../show-product-images-dialog/show-product-images-dialog.component';
import { Product } from '../../model/product.model';
import { ProductService } from '../../services/product/product.service';

@Component({
  selector: 'app-show-product-detailes',
  templateUrl: './show-product-detailes.component.html',
  styleUrls: ['./show-product-detailes.component.css']
})
export class ShowProductDetailesComponent implements OnInit {

  showLoadMoreProductButton = false;
  showTable = false;
  pageNumber: number = 0;
  productDetails : Product[] =[];
  displayedColumns: string[] = ['Id', 'Nombre del Producto', 'Descripción del Producto', 'Precio con Descuento', 'Precio Actual' ,'Acciones'];
  constructor(private productService: ProductService ,
    public imagesDialog: MatDialog,
    private imageProcessingService: ImageProcessingService,
    private router: Router) { }

  ngOnInit(): void {
    this.getAllProducts();
  }

  searchByKeyword(searchkeyword){

    this.pageNumber= 0;
    this.productDetails= [];
    this.getAllProducts(searchkeyword);

  }

  public getAllProducts(searchKey: string =""){
    this.showTable = false;
    this.productService.getAllProducts(this.pageNumber, searchKey)
    .pipe(
      map((x: Product[], i) => x.map((product: Product) => this.imageProcessingService.createImages(product)))
    )
    .subscribe(
      (resp: Product[]) =>{
        console.log(resp);
        resp.forEach(product => this.productDetails.push(product));
        this.showTable=true;
        if(resp.length==2){
          this.showLoadMoreProductButton=true;
        }else{
          this.showLoadMoreProductButton=false;
        }
        // this.productDetails = resp;
      }, (error: HttpErrorResponse) => {
        console.log(error);
      }

    );
  }

  loadMoreProduct(){
    this.pageNumber= this.pageNumber+1;
    this.getAllProducts();
  }

  deleteProduct(productId) {
    this.productService.deleteProduct(productId).subscribe(
      () => {
        this.productDetails = this.productDetails.filter(product => product.productId !== productId);
        this.showTable = false; 
        setTimeout(() => {
          this.showTable = true; 
        }, 100); 
      },
      (error: HttpErrorResponse) => {
        console.log(error);
      }
    );
  }
  


  showImages(product: Product){
    console.log(product);
    this.imagesDialog.open(ShowProductImagesDialogComponent, {
      data: {
        images: product.productImages
      },
      height: '500px',
      width: '800px'
    });

  }

  editProductDetails(productId){
    this.router.navigate(['/addNewProduct', {productId: productId}])
  }

}
