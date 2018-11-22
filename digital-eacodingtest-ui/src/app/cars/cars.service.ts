import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';

import { CarMake } from './car-make.model';
import { environment } from './../../environments/environment';

@Injectable()
export class CarsService {

  constructor(private http: HttpClient) {
  }

  listCarsByMake(): Observable<CarMake[]> {
    return this.http
      .get<CarMake[]>(environment.api_endpoints.cars.uri)
      .pipe(
        retry(environment.api_endpoints.cars.retries),
        catchError(_ => of([]))
      );
  }
}
