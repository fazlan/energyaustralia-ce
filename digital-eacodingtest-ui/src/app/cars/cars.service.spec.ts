import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { environment } from './../../environments/environment';
import { CarMake } from './car-make.model';
import { CarsService } from './cars.service';

describe('CarsService', () => {

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CarsService]
    })
      .compileComponents();
  });

  it('should return cars when GET cars successful', () => {
    const response = [];
    const car: CarMake = { make: 'Make one', shows: [] };
    const carsService: CarsService = TestBed.get(CarsService);
    const httpMock: HttpTestingController = TestBed.get(HttpTestingController);

    carsService.listCarsByMake()
      .subscribe(
        cars => Object.assign(response, cars),
        _ => fail('Not expected to reach')
      );

    const req = httpMock.expectOne(environment.api_endpoints.cars.uri);
    req.flush([car]);

    // Then
    expect(response).toEqual([car]);

    // And
    expect(req.request.url).toBe(environment.api_endpoints.cars.uri);
    expect(req.request.method).toBe('GET');

  });

  it('should return empty cars when GET cars unsuccessful', () => {
    const status = 400;
    const statusText = 'Bad request';
    const response = [];

    const carsService: CarsService = TestBed.get(CarsService);
    const httpMock: HttpTestingController = TestBed.get(HttpTestingController);

    carsService.listCarsByMake()
      .subscribe(
        _ => fail('Not expected to reach'),
        error => Object.assign(response, error)
      );

    const req = httpMock.expectOne(environment.api_endpoints.cars.uri);
    req.flush({}, { status, statusText });

    // Then
    expect(response).toEqual([]);

    // And
    expect(req.request.url).toBe(environment.api_endpoints.cars.uri);
    expect(req.request.method).toBe('GET');

  });
});
