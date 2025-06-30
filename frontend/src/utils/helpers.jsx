export const currencyFormatter = (price) =>
  new Intl.NumberFormat("en-EN", {
    style: "currency",
    currency: "USD",
    currencyDisplay: "narrowSymbol",
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
  }).format(price);

export const numberFormatter = (number) =>
  new Intl.NumberFormat("en-EN", {
    minimumFractionDigits: 0,
    maximumFractionDigits: 2,
  }).format(number);

export const dateFormatter = (date) => {
  return new Intl.DateTimeFormat("en-EN", {
    year: "numeric",
    month: "short",
    day: "2-digit",
  }).format(new Date(date));
};
