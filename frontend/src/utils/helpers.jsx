export const currencyFormatter = (price) =>
  new Intl.NumberFormat("pl-PL", {
    style: "currency",
    currency: "USD",
    currencyDisplay: "narrowSymbol",
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
  }).format(price);
