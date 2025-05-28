export const currencyFormatter = (price) =>
  new Intl.NumberFormat("pl-PL", {
    style: "currency",
    currency: "USD",
    currencyDisplay: "narrowSymbol",
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
  }).format(price);

export const numberFormatter = (number) =>
  new Intl.NumberFormat("pl-PL", {
    minimumFractionDigits: 0,
    maximumFractionDigits: 2,
  }).format(number);

export const dateFormatter = (date) => {
  return new Intl.DateTimeFormat("pl-PL", {
    year: "numeric",
    month: "short",
    day: "2-digit",
  }).format(new Date(date));
};
