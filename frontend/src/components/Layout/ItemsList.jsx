import { Link } from "react-router-dom";
import Pagination from "../helper/Pagination";
import { FaEdit, FaEye, FaTrash } from "react-icons/fa";
import {
  currencyFormatter,
  dateFormatter,
  numberFormatter,
} from "../../utils/helpers";

const ItemsList = ({
  pagination = false,
  labels = [],
  data = [],
  handleDelete = null,
  actions = {},
  path = "",
}) => {
  return (
    <>
      {pagination && (
        <Pagination
          currentPage={pagination.page}
          totalPages={pagination.totalPages}
          onPageChange={pagination.setPage}
        />
      )}
      <div className="bg-white rounded-lg shadow overflow-auto">
        <div
          className={`hidden sm:grid grid-cols-${
            data.length > 0 ? Object.keys(data[0]).length : 1
          } gap-4 p-4 bg-gray-50 text-xs font-medium text-gray-500 uppercase tracking-wider`}
        >
          {labels.length > 0 &&
            labels.map((label, index) => (
              <div
                key={index}
                className={
                  label.type === "Number" || label.type === "Currency"
                    ? "text-right"
                    : ""
                }
              >
                {label.name}
              </div>
            ))}
          <div className="text-center">Akcje</div>
        </div>
        <div className="divide-y divide-gray-200">
          {data.map((item) => (
            <div
              key={item.id}
              className={`grid grid-cols-1 sm:grid-cols-${
                Object.keys(item).length
              } items-center gap-4 p-4 hover:bg-pink-50 transition-colors duration-200`}
            >
              {Object.entries(item)
                .slice(1)
                .map(([_, value], index) => (
                  <div key={index}>
                    {labels[index].type === "Link" ? (
                      <Link
                        to={`/${path}/${item.id}`}
                        className="text-pink-600 hover:underline font-medium"
                        key={index}
                      >
                        {value}
                      </Link>
                    ) : (
                      <div
                        className={`text-sm text-gray-700 ${
                          labels[index].type === "Text-Long" ? "truncate" : ""
                        } ${
                          labels[index].type === "Number" ||
                          labels[index].type === "Currency"
                            ? "text-right"
                            : ""
                        }`}
                        key={index}
                      >
                        {labels[index].type === "Number"
                          ? numberFormatter(value)
                          : labels[index].type === "Currency"
                          ? currencyFormatter(value)
                          : labels[index].type === "Date"
                          ? dateFormatter(value)
                          : labels[index].type === "Type"
                          ? value
                              .toLowerCase()
                              .replace(/_/g, " ")
                              .replace(/\b\w/g, (c) => c.toUpperCase())
                          : value}
                      </div>
                    )}
                  </div>
                ))}
              <div className="flex justify-center space-x-4 text-gray-600">
                {actions.get && (
                  <Link
                    to={`/${path}/${item.id}`}
                    className="hover:text-pink-500 transition duration-200"
                  >
                    <FaEye />
                  </Link>
                )}
                {actions.put && (
                  <Link
                    to={`/${path}/${item.id}/edit`}
                    className="hover:text-pink-500 transition duration-200"
                  >
                    <FaEdit />
                  </Link>
                )}
                {actions.delete && (
                  <button
                    onClick={() => handleDelete(item.id)}
                    className="hover:text-pink-500 transition duration-200 cursor-pointer"
                  >
                    <FaTrash />
                  </button>
                )}
              </div>
            </div>
          ))}
        </div>
      </div>
      {pagination && (
        <Pagination
          currentPage={pagination.page}
          totalPages={pagination.totalPages}
          onPageChange={pagination.setPage}
        />
      )}
    </>
  );
};

export default ItemsList;
