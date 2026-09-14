// ─── User Domain Models ───
import { Page } from '../../../../shared/models/pagination.model';

export interface UserProfile {
  id: number;
  name: string;
  status: string;
}

export interface User {
  id: string;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  status: string;
  phone: string | null;
  profile: UserProfile;
}

