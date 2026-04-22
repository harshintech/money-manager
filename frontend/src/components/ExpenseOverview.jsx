import { Plus } from "lucide-react";
import React, { useEffect, useState } from "react";
import { prepareExpenseDateChartData } from "../util/chartUtils";
import CustomLineChart from "./chart/CustomLineChart";

const ExpenseOverview = ({ transactions, onAddExpense }) => {
  const [chartData, setChartData] = useState([]);
  useEffect(() => {
    const result = prepareExpenseDateChartData(transactions);
    console.log(result);
    setChartData(result);
    console.log("chartData:", chartData);

    return () => {};
  }, [transactions]);
  return (
    <div className="card">
      <div className="flex items-center justify-between">
        <div>
          <h5 className="text-lg">Expense Overview</h5>
          <p className="text-xs text-gray-400 mt-0">
            Track your earnings over time and analyze your expense trends.
          </p>
        </div>
        <button className="add-btn" onClick={onAddExpense}>
          <Plus size={15} className="text-lg" /> Add Expense
        </button>
      </div>
      <div className="mt-10">
        {/* create line chart */}
        <CustomLineChart data={chartData} />
      </div>
    </div>
  );
};

export default ExpenseOverview;
