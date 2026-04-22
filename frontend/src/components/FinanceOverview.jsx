import React from "react";
import CustomPieChart from "./chart/CustomPieChart";
import { addThousandsSeparator } from "../util/util";

const FinanceOverview = ({ totalBalance, totalIncome, totalExpense }) => {
  const COLORS = ["#59168B", "#A0090e", "#016630"];

  const balanceData = [
    { name: "Total Balance", amount: totalBalance },
    { name: "Total Expenses", amount: totalExpense },
    { name: "Total Income", amount: totalIncome },
  ];
  return (
    <div className="card">
      <div className="flex items-center justify-between">
        <h5 className="text-lg">Financial Overview</h5>
      </div>

      <div className="p-4 flex items-center justify-center">
        <CustomPieChart
          data={balanceData}
          label="Total Balance"
          totalAmount={`${addThousandsSeparator(totalBalance)}`}
          colors={COLORS}
          showTextAnchor
        />
      </div>
    </div>
  );
};

export default FinanceOverview;
