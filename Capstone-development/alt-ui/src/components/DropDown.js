import { useState } from "react";

export default function DropDown({ array, name, id }) {
  const [selectedElement, setSelectedElement] = useState(array);
  const [query, setQuery] = useState("");

  const filteredElements =
    query === ""
      ? array
      : array.filter((item) => {
          console.log(item);
          return item[name].toLowerCase().includes(query.toLowerCase());
        });

  return (
    <div className="md:col-span-5">
      <select
        id="countries"
        value={selectedElement}
        by="id"
        onChange={setSelectedElement}
        className="form-control h-10 border mt-1 rounded px-4 w-full bg-gray-50"
      >
        <option selected></option>
        {filteredElements.map((element) => (
          <option key={element[id]} value={element}>
            {element[name]}
          </option>
        ))}
      </select>
    </div>
  );
}
