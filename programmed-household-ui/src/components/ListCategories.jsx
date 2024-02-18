import React from "react";

const ListCategories = () => {
  const dummyData = [
    {
      categoryId: 1,
      name: "Biryani",
      meal: "LUNCH",
      day: "SUNDAY",
      difficulty: "HARD",
    },
    {
      categoryId: 2,
      name: "Dosa",
      meal: "Breakfast",
      day: "MONDAY",
      difficulty: "EASY",
    },
  ];
  return (
    <div className="container">
      <h2>List of Categories</h2>
      <table className="table table-striped table-bordered">
        <thead>
          <tr>
            <th>Category Id</th>
            <th>Name</th>
            <th>Meal</th>
            <th>Day</th>
            <th>Difficulty</th>
          </tr>
        </thead>
        <tbody>
          {dummyData.map((category) => (
            <tr key={category.categoryId}>
              <td>{category.name}</td>
              <td>{category.meal}</td>
              <td>{category.day}</td>
              <td>{category.difficulty}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ListCategories;
