import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../environments/environment';
import { User } from '../modules/user/user.module';

@Injectable({ providedIn: 'root' })
export class AuthenticationService {
    private currentUserSubject: BehaviorSubject<User>;
    public currentUser: Observable<User>;

    constructor(private http: HttpClient) {
        this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
        this.currentUser = this.currentUserSubject.asObservable();
    }

    public get currentUserValue(): User {
        return this.currentUserSubject.value;
    }
    
    login(useraccount:User) {
      const headers = new HttpHeaders(useraccount ? {
        authorization : 'Basic ' +btoa(useraccount.ssoId + ':' + useraccount.password)
    } : {});
    return this.http.post<any>('http://localhost:8080/login', {headers: headers})
    .pipe(map(user => {
      // store user details and basic auth credentials in local storage to keep user logged in between page refreshes
      user.authdata = window.top.btoa(user.ssoId + ':' + user.password);
      localStorage.setItem('currentUser', JSON.stringify(user));
      this.currentUserSubject.next(user);
      return user;
  }));
   }

    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('currentUser');
        this.currentUserSubject.next(null);
    }
}