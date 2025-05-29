import DateInput from "../helper/DateInput";
import NumberInput from "../helper/NumberInput";
import SelectInput from "../helper/SelectInput";
import TextInput from "../helper/TextInput";

const FormList = ({ inputs, sorting }) => {
  return (
    <form className="flex space-x-4 justify-between">
      <div className="flex flex-wrap gap-4 items-end">
        {inputs.map(
          (
            { type, label, placeholder, value, setValue, isMinus, options },
            index
          ) =>
            type === "text" ? (
              <TextInput
                label="Nazwa"
                placeholder="Wyszukaj po nazwie..."
                value={value}
                setValue={setValue}
                key={index}
              />
            ) : type === "select" ? (
              <SelectInput
                label={label}
                value={value}
                setValue={setValue}
                options={options}
                key={index}
              >
                <option value="">Wszystkie</option>
                {options.map((option) => (
                  <option key={option.value} value={option.value}>
                    {option.label}
                  </option>
                ))}
              </SelectInput>
            ) : type === "number" ? (
              <NumberInput
                label={label}
                placeholder={placeholder}
                isMinus={isMinus}
                value={value}
                setValue={setValue}
                key={index}
              />
            ) : (
              <DateInput
                label={label}
                value={value}
                setValue={setValue}
                key={index}
              />
            )
        )}
      </div>
      <SelectInput
        label="Sortowanie"
        value={sorting.sortOption}
        setValue={sorting.setSortOption}
      >
        <option value="">Sortuj przez</option>
        {sorting.options.map((option) => (
          <option key={option.value} value={option.value}>
            {option.label}
          </option>
        ))}
      </SelectInput>
    </form>
  );
};

export default FormList;
