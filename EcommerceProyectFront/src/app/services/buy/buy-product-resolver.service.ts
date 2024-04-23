import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ImageProcessingService } from '../image/image-processing.service';
import { Product } from '../../model/product.model';
import { ProductService } from '../product/product.service';

@Injectable({
  providedIn: 'root'
})
export class BuyProductResolverService implements Resolve<Product[]>{

  constructor(private ProductService: ProductService, 
    private imageProcessingService: ImageProcessingService) { }


  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Product[] | Observable<Product[]> | Promise<Product[]> {
    const id= route.paramMap.get("id");
    const isSingleProductCheckout = route.paramMap.get("isSingleProductCheckout")
    return this.ProductService.getProductDetails(isSingleProductCheckout, id)
    .pipe(
      map(
        (x: Product[], i)=> x.map((product : Product) => this.imageProcessingService.createImages(product))
      )
    );

  }
}
