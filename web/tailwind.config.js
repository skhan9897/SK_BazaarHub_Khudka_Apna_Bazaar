/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        brand: {
          50: '#F0ECFE',
          100: '#DED6FD',
          500: '#5B35E8',
          600: '#4D29D4',
          700: '#351A91',
          900: '#1F0C60',
        },
        accent: {
          400: '#FFC773',
          500: '#FFB547',
          600: '#E59B2E',
        },
        surface: {
          bg: '#F6F7FB',
          card: '#FFFFFF',
          dark: '#17172B',
          muted: '#77778C',
        }
      },
      fontFamily: {
        sans: ['Inter', '-apple-system', 'BlinkMacSystemFont', 'Segoe UI', 'Roboto', 'sans-serif'],
      },
      boxShadow: {
        card: '0 4px 20px -2px rgba(23, 23, 43, 0.05)',
        hover: '0 10px 30px -4px rgba(91, 53, 232, 0.12)',
      }
    },
  },
  plugins: [],
}
