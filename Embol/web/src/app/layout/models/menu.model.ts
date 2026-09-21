// ─── Sidebar / Layout Menu Models ───

export interface MenuItem {
  id: string;
  label: string;
  icon: string;
  route?: string;
  children?: MenuChildItem[];
  badge?: string;
  badgeColor?: string;
}

export interface MenuChildItem {
  id: string;
  label: string;
  route?: string;
  children?: MenuChildItem[];
}

export interface BreadcrumbItem {
  label: string;
  route?: string;
}

export interface HeaderBadge {
  label: string;
  colorClass: string;
}
