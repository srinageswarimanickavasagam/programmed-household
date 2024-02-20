// eslint-disable-next-line no-unused-vars
import React, { useState, useEffect } from "react";

const CheckBackend = () => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch("http://localhost:5000/api/v1/recipes", {
          method: "GET", // Use GET method
          headers: {
            "Content-Type": "application/json", // No need to set Content-Type for GET requests
          },
        });
        if (!response.ok) {
          throw new Error("Failed to fetch data");
        }
        const jsonData = await response.json();
        setData(jsonData.data.content); // Update to access the content array
        setLoading(false);
      } catch (error) {
        console.error("Error fetching data:", error);
        // Handle error, e.g., set an error state or show an error message
      }
    };

    fetchData();
  }, []);

  return (
    <div>
      {loading ? (
        <p>recipes loading...</p>
      ) : (
        <div>
          {data.map((recipe) => (
            <div key={recipe.recipeId}>
              <h3>{recipe.title}</h3>
              <p>
                Reference:{" "}
                <a
                  href={recipe.reference}
                  target="_blank"
                  rel="noopener noreferrer"
                >
                  {recipe.reference}
                </a>
              </p>
              <p>Scheduled Date: {recipe.scheduledOn}</p>
              <p>Schedule Day: {recipe.scheduleDay}</p>
              <p>Meal: {recipe.meal}</p>
              <>
                <p>Ingredients:</p>
                <ul>
                  {Object.entries(recipe.ingredients).map(
                    ([method, ingredients]) => (
                      <li key={method}>
                        <strong>{method.toUpperCase()}:</strong>
                        <ul>
                          {Object.entries(ingredients).map(
                            ([ingredient, amount]) => (
                              <li key={ingredient}>
                                {ingredient}: {amount}
                              </li>
                            )
                          )}
                        </ul>
                      </li>
                    )
                  )}
                </ul>
              </>
              {/* Add more details as needed */}
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default CheckBackend;
