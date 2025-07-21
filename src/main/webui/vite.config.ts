import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import viteTsconfigPaths from 'vite-tsconfig-paths'
import tailwindcss from '@tailwindcss/vite'

// https://vite.dev/config/
export default defineConfig({
  // depending on your application, base can also be "/"
  base: '/',
  build: {
    outDir: 'dist',
  },
  assetsInclude: ['src/**/*.md'] ,
  plugins: [react(), viteTsconfigPaths(), tailwindcss()],
  server: {
    host: '127.0.0.1',
    // this ensures that the browser opens upon server start
    open: false,
    // this sets a default port to 3000, you can change this
    port: 3000,
    // this enables hot module reloading for development
    hmr: true
  },
})