import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from "path"

export default defineConfig({
  resolve: {   
    alias:{
      '@': path.resolve( __dirname, './src' )
    }
  },
  plugins: [vue()],
  lintOnSave: false,
  productionSourceMap: false,
  publicPath: "/",
  outputDir: 'dist',
  assetsDir: 'assets',
  server: {
    port: 3002,
    host: '0.0.0.0',
    https: false,
    open: false,
    proxy: {
      "/api/dev": {
        target: "http://localhost:3001",
        changeOrigin: true,
      }
    },
    client: {
      overlay: false
    }
  }
})
