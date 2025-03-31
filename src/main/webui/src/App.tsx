import type { FC } from 'react'
import './App.css'
import { AppRoutes } from './pages'
import { withErrorBoundary, type ErrorBoundaryProps, type FallbackProps } from 'react-error-boundary'

const FallbackComponent: FC<FallbackProps> = (props) => {
  const { error: { message, stack }, resetErrorBoundary } = props
  return (
    <section className="error-boundary">
      <h1>App Error:</h1>
      <h5>{message ?? "Unknown error"}.</h5>
      <pre>
        {stack ?? "No stack trace available."}
      </pre>
      <button onClick={resetErrorBoundary}>Retry</button>
    </section>
  )
}

function App() {
  const errorProps: ErrorBoundaryProps = { FallbackComponent }
  const AppRoutesWithErrorBoundary = withErrorBoundary(AppRoutes, errorProps)
  return (
    <>
      <AppRoutesWithErrorBoundary />
    </>
  )
}

export default App
