import React, { useEffect, useState } from "react";
import EmojiPickerPopup from "./EmojiPickerPopup";
import Input from "./Input";
import { LoaderCircle } from "lucide-react";

const AddIncomeForm = ({ onAddIncome, categories }) => {
  const [income, setIncome] = useState({
    name: "",
    amount: "",
    date: "",
    icon: "",
    categoryId: "",
  });

  const [loading, setLoading] = useState(false);

  const categoryOptions = categories.map((category) => ({
    value: category.id,
    label: category.name,
  }));

  const handleChange = (key, value) => {
    setIncome({ ...income, [key]: value });
  };

  const handleAddIncome = async () => {
    setLoading(true);
    try {
      await onAddIncome(income);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (categories.length > 0 && !income.categoryId) {
      setIncome((prev) => ({ ...prev, categoryId: categories[0].id }));
    }
  }, [categories, income.categoryId]);

  return (
    <div>
      <EmojiPickerPopup
        icon={income.icon}
        onSelect={(selectedIcon) => handleChange("icon", selectedIcon)}
      />

      <Input
        value={income.name}
        onChange={(e) => handleChange("name", e.target.value)}
        label="e.g., Salary, Freelance, Bonus"
        type="text"
      />

      <Input
        label="Category"
        value={income.categoryId}
        onChange={(e) => handleChange("categoryId", e.target.value)}
        isSelect={true}
        options={categoryOptions}
      />

      <Input
        value={income.amount}
        onChange={(e) => handleChange("amount", e.target.value)}
        label="Amount"
        placeholder="e.g., 500.00"
        type="number"
      />

      <Input
        value={income.date}
        onChange={(e) => handleChange("date", e.target.value)}
        label="Date"
        placeholder=""
        type="date"
      />

      <div className="flex justify-end mt-6">
        <button
          onClick={handleAddIncome}
          disabled={loading}
          className="add-btn add-btn-fill"
        >
          {loading ? (
            <>
              <LoaderCircle className="animates-spin w-4 h-4" /> Adding...{" "}
            </>
          ) : (
            <>Add Income</>
          )}
        </button>
      </div>
    </div>
  );
};

export default AddIncomeForm;
