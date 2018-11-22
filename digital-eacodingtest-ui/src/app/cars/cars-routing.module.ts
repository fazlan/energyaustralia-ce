import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CarsMakeListComponent } from './cars-make-list/cars-make-list.component';

const routes: Routes = [
  {
    path: '',
    component: CarsMakeListComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CarsRoutingModule {
}
