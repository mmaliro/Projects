# Router:

npm install react-router-dom@6


# Install Tailwind CSS:

1. npm install -D tailwindcss
2. npx tailwindcss init
3. Configure your template paths
Add the paths to all of your template files in your tailwind.config.js file.

content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],

4. Add the Tailwind directives to your CSS
Add the @tailwind directives for each of Tailwind’s layers to your ./src/index.css file.

@tailwind base;
@tailwind components;
@tailwind utilities;

# For Icons
npm install @headlessui/react 
npm install @heroicons/react

