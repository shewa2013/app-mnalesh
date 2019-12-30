import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { User } from '../modules/user/user.module';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
@Injectable({ providedIn: 'root' })
export class UserService {
    constructor(private http: HttpClient) { }
    private currentUserSubject: BehaviorSubject<User>;
    getAll() {
        return this.http.get<User[]>(`${environment.apiUrl}/users`);
    }
    login(useraccount:User) {
      const headers = new HttpHeaders(useraccount ? {
        authorization : 'Basic ' +btoa(useraccount.ssoId + ':' + useraccount.password)
    } : {});
    return this.http.get<any>(`${environment.apiUrl}/` + 'login', {headers: headers})
    .pipe(map(user => {
     if (user && user.ssoId) {
         localStorage.setItem('currentUser', JSON.stringify('Basic ' +btoa(user.ssoId + ':' + user.password)));
         this.currentUserSubject.next(user);
     }
   
     return user;
         }));
   }
}