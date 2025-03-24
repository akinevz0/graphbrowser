import { compiler } from "markdown-to-jsx"
import { useEffect, useState } from "react"



const HomePageComponent = () => {
  const [state, setState] = useState<string|null>(null)
  useEffect(() => {
    (async () => {
      const response = await fetch("/src/index.md")
      const text = await response.text()
      setState(text)
    })()
  }, [])
  if (!state)
    return <></>
  return <>{compiler(state)}</>
}

export default HomePageComponent
