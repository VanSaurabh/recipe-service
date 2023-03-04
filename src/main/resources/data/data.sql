-- recipe table

-- Insert a recipe for spaghetti carbonara
INSERT INTO recipe (id, recipe_name, is_vegetarian, servings, instructions, is_deleted)
VALUES (1, 'spaghetti carbonara', false, 4, 'Cook spaghetti according to package instructions. Meanwhile, cook bacon until crispy. Beat eggs and mix in parmesan cheese and black pepper. Drain spaghetti and add to the pan with the bacon. Remove from heat and quickly stir in egg mixture. Serve immediately.', false);

-- Insert a recipe for lentil soup
INSERT INTO recipe (id, recipe_name, is_vegetarian, servings, instructions)
VALUES (2, 'lentil soup', true, 6, 'Heat olive oil in a large pot over medium heat. Add onions, carrots, and celery and sauté for a few minutes. Add garlic and cook for another minute. Add lentils, tomatoes, vegetable broth, bay leaves, and thyme. Bring to a boil, then reduce heat and simmer for 30-40 minutes, until lentils are tender. Season with salt and black pepper to taste.');

-- Insert a recipe for chocolate chip cookies
INSERT INTO recipe (id, recipe_name, is_vegetarian, servings, instructions, is_deleted)
VALUES (3, 'chocolate chip cookies', true, 24, 'Preheat oven to 375°F. Cream butter, brown sugar, and granulated sugar until light and fluffy. Beat in eggs and vanilla extract. In a separate bowl, mix together flour, baking soda, and salt. Gradually stir flour mixture into butter mixture. Fold in chocolate chips. Drop dough by spoonfuls onto baking sheet. Bake for 8-10 minutes or until golden brown. Cool on wire rack.', false);

-- Insert a recipe for chicken fajitas
INSERT INTO recipe (id, recipe_name, is_vegetarian, servings, instructions, is_deleted)
VALUES (4, 'chicken fajitas', false, 4, 'Slice chicken breasts, bell peppers, and onion into thin strips. Cook in a pan over medium-high heat with fajita seasoning until chicken is cooked through and vegetables are tender-crisp. Serve with warm flour tortillas, sour cream, salsa, shredded cheese, and guacamole.', false);

-- Insert a recipe for tomato soup
INSERT INTO recipe (id, recipe_name, is_vegetarian, servings, instructions, is_deleted)
VALUES (5, 'tomato soup', true, 4, 'Heat olive oil in a large pot over medium heat. Add onions, carrots, and celery and sauté for a few minutes. Add garlic and cook for another minute. Add tomatoes, vegetable broth, bay leaf, sugar, salt, and black pepper. Bring to a boil, then reduce heat and simmer for 20-30 minutes. Remove bay leaf and blend soup until smooth. Stir in heavy cream and serve.', false);

-- Insert a recipe for spaghetti bolognesea
INSERT INTO recipe (id, recipe_name, is_vegetarian, servings, instructions, is_deleted)
VALUES (6, 'spaghetti bolognese', false, 6, 'Cook spaghetti according to package instructions. Meanwhile, cook ground beef in a large skillet over medium-high heat until browned. Add onions and garlic and cook until softened. Add tomato sauce, canned tomatoes, beef broth, oregano, salt, and black pepper. Simmer for 15-20 minutes. Serve sauce over cooked spaghetti.', false);

-- Insert a recipe for vegetable stir-fry
INSERT INTO recipe (id, recipe_name, is_vegetarian, servings, instructions, is_deleted)
VALUES (7, 'vegetable stir-fry', true, 4, 'Slice broccoli, carrots, snow peas, red bell pepper, and onion into bite-sized pieces. Heat vegetable oil in a wok or large skillet over high heat. Add garlic and stir-fry for 30 seconds. Add vegetables and stir-fry for 3-4 minutes until crisp-tender. Mix soy sauce, rice vinegar, and cornstarch in a small bowl. Pour over vegetables and stir-fry for another minute until sauce thickens. Serve with rice.', false);

-- Insert a recipe for chicken parmesan
INSERT INTO recipe (id, recipe_name, is_vegetarian, servings, instructions, is_deleted)
VALUES (8, 'chicken parmesan', false, 4, 'Preheat oven to 375°F. In a bowl, mix breadcrumbs, grated parmesan cheese, garlic powder, salt, and black pepper. In a separate bowl, beat eggs. Coat chicken breasts in flour, then dip in egg mixture, then coat in breadcrumb mixture. Heat oil in a pan and cook chicken until golden brown on both sides. In a baking dish, layer cooked chicken, tomato sauce, and mozzarella cheese. Bake for 20-25 minutes, until cheese is melted and bubbly. Serve with spaghetti.', false);

-- Insert a recipe for mushroom risotto
INSERT INTO recipe (id, recipe_name, is_vegetarian, servings, instructions, is_deleted)
VALUES (9, 'mushroom risotto', true, 4, 'In a large pot, heat olive oil and butter over medium heat. Add chopped onions and cook until softened. Add minced garlic and cook for another minute. Add sliced mushrooms and cook until tender. Add arborio rice and stir until coated in oil and butter. Add white wine and cook until absorbed. Add vegetable broth, one ladleful at a time, stirring constantly and waiting for broth to be absorbed before adding more. Continue adding broth and stirring until rice is tender and creamy. Stir in grated parmesan cheese and season with salt and black pepper to taste.', false);

-- Insert a recipe for black bean enchiladas
INSERT INTO recipe (id, recipe_name, is_vegetarian, servings, instructions, is_deleted)
VALUES (10, 'black bean enchiladas', true, 6, 'Preheat oven to 350°F. In a bowl, mix together drained and rinsed black beans, chopped onion, diced jalapeño pepper, chopped cilantro, lime juice, salt, and black pepper. Spoon mixture onto tortillas and roll up. Place rolled tortillas seam-side down in a baking dish. Pour enchilada sauce over the top and sprinkle with shredded cheese. Bake for 20-25 minutes, until cheese is melted and bubbly.', false);




-- ingredients table


INSERT INTO recipe_entity_ingredients(recipe_entity_id, ingredients)
VALUES (1, '{"spaghetti", "eggs", "bacon", "parmesan cheese", "black pepper"}');

INSERT INTO recipe_entity_ingredients(recipe_entity_id, ingredients)
VALUES (2, '{"lentils", "carrots", "celery", "onion", "garlic", "tomatoes", "vegetable broth", "bay leaves", "thyme", "salt", "black pepper"}');

INSERT INTO recipe_entity_ingredients(recipe_entity_id, ingredients)
VALUES (3, '{"butter", "brown sugar", "granulated sugar", "eggs", "vanilla extract", "all-purpose flour", "baking soda", "salt", "chocolate chips"}');

INSERT INTO recipe_entity_ingredients(recipe_entity_id, ingredients)
VALUES (4, '{"chicken breasts", "bell peppers", "onion", "fajita seasoning", "flour tortillas", "sour cream", "salsa", "shredded cheese", "guacamole"}');

INSERT INTO recipe_entity_ingredients(recipe_entity_id, ingredients)
VALUES (5, '{"tomatoes", "carrots", "celery", "onion", "garlic", "vegetable broth", "olive oil", "bay leaf", "sugar", "salt", "black pepper", "heavy cream"}');

INSERT INTO recipe_entity_ingredients(recipe_entity_id, ingredients)
VALUES (6, '{"spaghetti", "ground beef", "onion", "garlic", "tomato sauce", "canned tomatoes", "beef broth", "oregano", "salt", "black pepper"}');

INSERT INTO recipe_entity_ingredients(recipe_entity_id, ingredients)
VALUES (7, '{"broccoli", "carrots", "snow peas", "red bell pepper", "onion", "garlic", "soy sauce", "rice vinegar", "cornstarch", "vegetable oil"}');

INSERT INTO recipe_entity_ingredients(recipe_entity_id, ingredients)
VALUES (8, '{"chicken breasts", "breadcrumbs", "parmesan cheese", "mozzarella cheese", "tomato sauce", "spaghetti", "eggs", "flour", "garlic powder", "salt", "black pepper"}');

INSERT INTO recipe_entity_ingredients(recipe_entity_id, ingredients)
VALUES (9, '{"arborio rice", "mushrooms", "onion", "garlic", "vegetable broth", "white wine", "parmesan cheese", "butter", "olive oil", "salt", "black pepper"}');

INSERT INTO recipe_entity_ingredients(recipe_entity_id, ingredients)
VALUES (10, '{"black beans", "enchilada sauce", "tortillas", "shredded cheese", "onion", "jalapeño pepper", "cilantro", "lime", "salt", "black pepper"}');
