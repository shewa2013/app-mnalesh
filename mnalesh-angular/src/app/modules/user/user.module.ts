import { UserProfile } from '../userprofile.module';
export class User {
  id:number;
  ssoId:string;
  password:string;
  firstName:string;
  lastName:string;
  email:string;
  state:string;
  userProfiles:Set<UserProfile>;
}