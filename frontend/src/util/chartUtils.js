export const prepareIncomeDateChartData = (transactions) => {
  const map = {};

  transactions.forEach((item) => {
    const d = new Date(item.date);

    const key = d.toISOString().split("T")[0];

    if (!map[key]) {
      map[key] = 0;
    }

    map[key] += item.amount;
  });

  return Object.keys(map)
    .map((dateStr) => {
      const d = new Date(dateStr);

      return {
        rawDate: d,
        date: d.toLocaleDateString("en-IN", {
          day: "2-digit",
          month: "short",
        }),
        amount: map[dateStr],
      };
    })
    .sort((a, b) => a.rawDate - b.rawDate)
    .map(({ date, amount }) => ({ date, amount }));
};

export const prepareExpenseDateChartData = (transactions) => {
  const map = {};

  transactions.forEach((item) => {
    const d = new Date(item.date);

    const key = d.toISOString().split("T")[0];

    if (!map[key]) {
      map[key] = 0;
    }

    map[key] += item.amount;
  });

  return Object.keys(map)
    .map((dateStr) => {
      const d = new Date(dateStr);

      return {
        rawDate: d,
        date: d.toLocaleDateString("en-IN", {
          day: "2-digit",
          month: "short",
        }),
        amount: map[dateStr],
      };
    })
    .sort((a, b) => a.rawDate - b.rawDate)
    .map(({ date, amount }) => ({ date, amount }));
};
