import React from "react";
import { PieChart, Pie, Cell, ResponsiveContainer, Tooltip } from "recharts";

const CustomPieChart = ({
  data = [],
  label,
  totalAmount,
  colors = [],
  showTextAnchor = false,
}) => {
  return (
    <div className="relative w-full h-72 mt-10">
      <ResponsiveContainer>
        <PieChart>
          <Pie
            data={data}
            dataKey="amount"
            nameKey="name"
            innerRadius={90}
            outerRadius={125}
            paddingAngle={2}
          >
            {data.map((entry, index) => (
              <Cell
                key={`cell-${index}`}
                fill={colors[index % colors.length]}
              />
            ))}
          </Pie>

          <Tooltip formatter={(value) => `₹${value.toLocaleString()}`} />
        </PieChart>
      </ResponsiveContainer>

      {/* 🔥 Center Text */}
      {showTextAnchor && (
        <div className="absolute inset-0 flex flex-col items-center justify-center pointer-events-none">
          <p className="text-xs text-gray-500">{label}</p>
          <p className="text-lg font-semibold text-gray-800">₹{totalAmount}</p>
        </div>
      )}
    </div>
  );
};

export default CustomPieChart;
