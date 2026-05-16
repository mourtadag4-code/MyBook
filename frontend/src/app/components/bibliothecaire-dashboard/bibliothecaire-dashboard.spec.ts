import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BibliothecaireDashboard } from './bibliothecaire-dashboard';

describe('BibliothecaireDashboard', () => {
  let component: BibliothecaireDashboard;
  let fixture: ComponentFixture<BibliothecaireDashboard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BibliothecaireDashboard],
    }).compileComponents();

    fixture = TestBed.createComponent(BibliothecaireDashboard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
