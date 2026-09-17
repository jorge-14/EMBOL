/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      colors: {
        brand: {
          DEFAULT: 'rgb(var(--color-brand) / <alpha-value>)',
          hover: 'rgb(var(--color-brand-hover) / <alpha-value>)'
        },
        'table-header-accent': 'rgb(var(--color-table-header-accent) / <alpha-value>)',
        'table-footer-accent': 'rgb(var(--color-table-footer-accent) / <alpha-value>)'
      }
    },
  },
  plugins: [],
}
