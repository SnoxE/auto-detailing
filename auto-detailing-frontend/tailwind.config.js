/** @type {import('tailwindcss').Config} */
const defaultTheme = require('tailwindcss/defaultTheme')

module.exports = {
  content: ['./index.html', './src/**/*.{vue, js, ts}'],
  theme: {
    extend: {
      fontFamily: {
        sans: ['Open Sans', ...defaultTheme.fontFamily.sans]
      },
      colors: {
        'mcl-orange': '#ff6d01',
        'mcl-pale-orange': '#ff984a',
        'mcl-okx-pink': '#fe1872',
        'dark-mode-gray': '#121212',
        'dark-gray': '#222222',
        'light-gray': '#363636',
        'xkom-gray': '#a5a5a5'
      },
      boxShadow: {
        'mcl-orange': '0 0 3px 3px #ff6d01',
        'mcl-pale-orange': '0 0 3px 3px #ff984a'
      }
    }
  },
  plugins: []
}
