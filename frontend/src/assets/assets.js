import { Coins, FunnelPlus, LayoutDashboard, List, Wallet } from "lucide-react";
import login_bg from "./login_bg.jpg";
import login_bg2 from "./login_bg2.jpg";

export const assets = {
  login_bg,
  login_bg2,
};

export const SIDE_BAR_DATA = [
  {
    id: "01",
    label: "Dashboard",
    icon: LayoutDashboard,
    path: "/dashboard",
  },
  {
    id: "02",
    label: "Category",
    icon: List,
    path: "/category",
  },
  {
    id: "03",
    label: "Income",
    icon: Wallet,
    path: "/income",
  },
  {
    id: "04",
    label: "Expense",
    icon: Coins,
    path: "/expense",
  },
  {
    id: "05",
    label: "Filters",
    icon: FunnelPlus,
    path: "/filter",
  },
];
