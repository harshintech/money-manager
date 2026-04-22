import { Download, Loader, Mail } from "lucide-react";
import React, { useState } from "react";
import TransactionInfoCard from "./TransactionInfoCard";
import moment from "moment";

const ExpenseList = ({ transactions, onDelete, onDownload, onEmail }) => {
  const [emailloading, setEmailLoading] = useState(false);
  const [downloadloading, setDownloadLoading] = useState(false);

  const handleEmail = async () => {
    setEmailLoading(true);
    try {
      await onEmail();
    } finally {
      setEmailLoading(false);
    }
  };

  const handleDownload = async () => {
    setDownloadLoading(true);
    try {
      await onDownload();
    } finally {
      setDownloadLoading(false);
    }
  };
  return (
    <div className="card">
      <div className="flex items-center justify-between">
        <h5 className="text-lg">Expense Source</h5>
        <div className="flex items-center justify-end gap-2">
          <button disabled={emailloading} className="card-btn" onClick={handleEmail}>
            {emailloading ? (
              <>
                <Loader className="w-4 h-4 animate-spin" /> Emailing...
              </>
            ) : (
              <>
                <Mail size={15} className="text-base" /> Email
              </>
            )}
          </button>
          <button
            disabled={downloadloading}
            className="card-btn"
            onClick={handleDownload}
          >
            {downloadloading ? (
              <>
                <Loader className="w-4 h-4 animate-spin" /> Downloading...
              </>
            ) : (
              <>
                <Download size={15} className="text-base" />
                Download
              </>
            )}
          </button>
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2">
        {/* display the expenses */}
        {transactions?.map((expense) => (
          <TransactionInfoCard
            key={expense.id}
            title={expense.name}
            icon={expense.icon}
            date={moment(expense.date).format("Do MMM YYYY")}
            amount={expense.amount}
            type="expense"
            onDelete={() => onDelete(expense.id)}
          />
        ))}
      </div>
    </div>
  );
};

export default ExpenseList;
