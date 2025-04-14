// import { ScrollArea } from "@base-ui-components/react/scroll-area";
import type { FC } from "react";
import Markdown, { type UrlTransform } from "react-markdown";
import { useLocation } from "react-router-dom";
import remarkGfm from "remark-gfm";


interface LinkRendererProps {
    children: string
}

function jsonToMarkdownList<T>(data: T, indent = 0): string {
    const INDENT = '  '.repeat(indent);

    if (typeof data === 'string' || typeof data === 'number' || typeof data === 'boolean' || data === null) {
        return String(data);
    }

    if (Array.isArray(data)) {
        return data
            .map((item) => `${jsonToMarkdownList(item, indent + 1)}`)
            .join('\n');
    }

    if (typeof data === 'object') {
        return Object.entries(data)
            .map(([key, value]) => {
                const valueStr = jsonToMarkdownList(value, indent + 1);
                const isMultiline = valueStr.includes('\n');
                if (isMultiline) {
                    return `${INDENT}- **${key}**:\n${valueStr}`;
                } else {
                    return `${INDENT}- **${key}**: ${valueStr}`;
                }
            })
            .join('\n');
    }

    return '';
}

const LinkRenderer: FC<LinkRendererProps> = ({ children }) => {
    const location = useLocation();
    const urlTransformer: UrlTransform = function (url, key) {
        if (key === 'href')
            return `${location.pathname}?q=${encodeURIComponent(url)}`
        return url;
    }

    // const markdown = children
    const markdown = jsonToMarkdownList(JSON.parse(children))
    console.log(markdown)
    return (

        <Markdown
            remarkPlugins={[remarkGfm]}
            urlTransform={urlTransformer}
            components={{
                // a: ({ href, children }) => (
                //     <Link to={href ?? '#'}>{children}</Link>
                // ),
                // Render entire block inside <pre> to preserve whitespace
            }}
        >
            {markdown}
        </Markdown>
    );
    // return <div className="codeStyle">
    //     {
    //         lines.map((line, index) => <Markdown
    //             key={index}
    //             remarkPlugins={[remarkGfm]}
    //             urlTransform={urlTransformer}
    //             components={{
    //                 a: ({ href, children }) => <Link to={href ?? '#'}>{children}</Link>,
    //                 // p: ({ children }) => <pre style={{ margin: 0 }}>{children}</pre>
    //             }}
    //         >
    //             {line}
    //         </Markdown>)
    //     }
    // </div>
}
// const codeStyle = "bg-black-400 text-white-600 rounded-lg p-2 font-mono whitespace-pre overflow-auto"

const Area: FC<{ children: string; }> = ({ children }) => {
    return (
        <section className="codeStyle max-w-full">
            <LinkRenderer>
                {children}
            </LinkRenderer>
        </section>
    )
}

export const DataTable: FC<{ json: string; }> = ({ json }) => {
    try {
        return <Area>{json}</Area>
    } catch (e) {
        return <Area>
            {`Invalid JSON: ${json}\nError while parsing: ${e}`}
        </Area>
    }
};
