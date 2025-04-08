import { useQuery } from '@tanstack/react-query'
import type { FC, ReactNode } from 'react'
import { Loading } from './Loading'
import { useErrorBoundary } from 'react-error-boundary'
import { DataTable } from './DataTable'

type Result = ({ url: string, title: string })[]

const apiCount = "/api/count"

const apiAll = "/api/all"

const fetchCount = () => fetch(apiCount).then((res) => res.json() as Promise<number>)

const fetchAll = () => fetch(apiAll).then((res) => res.json() as Promise<Result>)

const DatabaseCountFetch: FC<{ children: ({ count }: { count: number }) => ReactNode }> = ({ children }) => {
    const { showBoundary } = useErrorBoundary()
    const { isPending, error, data } = useQuery({
        queryKey: [apiCount],
        queryFn: fetchCount
    })
    if (isPending) return <Loading />
    if (error) showBoundary(error)

    return children({ count: data ?? 0 })
}

const DatabaseAllFetch: FC<{ children: ({ result }: { result: Result }) => ReactNode }> = ({ children }) => {
    const { showBoundary } = useErrorBoundary()
    const { isPending, error, data } = useQuery({
        queryKey: [apiAll],
        queryFn: fetchAll
    })
    if (isPending) return <Loading />
    if (error) showBoundary(error)

    return children({ result: data ?? [] })
}

const MetaPageComponent = () => {
    // return <Fetch url={apiCount}>
    //     {
    //         ({ data }) => {
    //             return <>
    //                 <div> There are {data} items in the database.</div>
    //                 {data && <Fetch url={apiAll}>
    //                     {({ data }) => {
    //                         return (
    //                             <pre> {data} </pre>
    //                         )
    //                     }}
    //                 </Fetch>
    //                 }
    //             </>
    //         }
    //     }
    // </Fetch>
    return <>
        <DatabaseCountFetch>
            {
                ({ count }) => (
                    (count === 0)
                        ? <div>{"There are no items in the database"}</div>
                        : <DatabaseAllFetch children={function ({ result }: { result: Result }): ReactNode {
                            return <DataTable json={JSON.stringify(result)} />
                        }} />
                )
            }
        </DatabaseCountFetch>
    </>
}

export default MetaPageComponent