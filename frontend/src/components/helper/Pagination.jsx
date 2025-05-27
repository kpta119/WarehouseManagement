const Pagination = ({ currentPage, totalPages, onPageChange }) => {
  return totalPages > 1 ? (
    <div className="flex justify-left gap-8 items-center mt-4">
      <button
        onClick={() => onPageChange((p) => Math.max(p - 1, 1))}
        disabled={currentPage === 1}
        className="bg-pink-500 hover:bg-pink-600 text-white px-4 py-2 rounded-lg transition disabled:opacity-50 disabled:cursor-not-allowed cursor-pointer"
      >
        Poprzednia
      </button>
      <span>
        Strona {currentPage} z {totalPages}
      </span>
      <button
        onClick={() => onPageChange((p) => p + 1)}
        disabled={currentPage >= totalPages}
        className="bg-pink-500 hover:bg-pink-600 text-white px-4 py-2 rounded-lg transition disabled:opacity-50 disabled:cursor-not-allowed cursor-pointer"
      >
        Następna
      </button>
    </div>
  ) : null;
};

export default Pagination;
