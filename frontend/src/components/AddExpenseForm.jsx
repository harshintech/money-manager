import React, { useEffect, useState } from "react";
import EmojiPickerPopup from "./EmojiPickerPopup";
import Input from "./Input";
import { LoaderCircle } from "lucide-react";

const AddExpenseForm = ({ onAddExpense, categories }) => {
  const [expense, setExpense] = useState({
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
    setExpense({ ...expense, [key]: value });
  };

  const handleAddExpense = async () => {
    setLoading(true);
    try {
      await onAddExpense(expense);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (categories.length > 0 && !expense.categoryId) {
      setExpense((prev) => ({ ...prev, categoryId: categories[0].id }));
    }
  }, [categories, expense.categoryId]);

  return (
    <div>
      <EmojiPickerPopup
        icon={expense.icon}
        onSelect={(selectedIcon) => handleChange("icon", selectedIcon)}
      />

      <Input
        value={expense.name}
        onChange={(e) => handleChange("name", e.target.value)}
        label="e.g., Salary, Freelance, Bonus"
        type="text"
      />

      <Input
        label="Category"
        value={expense.categoryId}
        onChange={(e) => handleChange("categoryId", e.target.value)}
        isSelect={true}
        options={categoryOptions}
      />

      <Input
        value={expense.amount}
        onChange={(e) => handleChange("amount", e.target.value)}
        label="Amount"
        placeholder="e.g., 500.00"
        type="number"
      />

      <Input
        value={expense.date}
        onChange={(e) => handleChange("date", e.target.value)}
        label="Date"
        placeholder=""
        type="date"
      />

      <div className="flex justify-end mt-6">
        <button
          onClick={handleAddExpense}
          disabled={loading}
          className="add-btn add-btn-fill"
        >
          {loading ? (
            <>
              <LoaderCircle className="animates-spin w-4 h-4" /> Adding...{" "}
            </>
          ) : (
            <>Add Expense</>
          )}
        </button>
      </div>
    </div>
  );
};

export default AddExpenseForm;
