import { Field } from '@base-ui-components/react/field';
import type { ClipboardEventHandler, KeyboardEventHandler } from 'react';

const UrlInput = () => {

    const validateURL = (input: unknown) => {
        try {
            // TODO: add a fetch with timeout to check whether the website is responding
            if (typeof input === "string" && new URL(input).toString()) { return null }
            // eslint-disable-next-line @typescript-eslint/no-unused-vars, no-empty
        } catch (e) { }
        return `Invalid input URL: ${input}`.toString()
    }

    const onKeyDown: KeyboardEventHandler = (event): void => {
        const { target, key } = event
        if (target instanceof HTMLInputElement)
            if (key === "Enter") redirect(viewPage(target.value), contains(target.dataset, "valid"))
    }

    const onPaste: ClipboardEventHandler = (event): void => {
        const { target, clipboardData: { items: [text] } } = event
        if (target instanceof HTMLInputElement)
            text.getAsString((data) => redirect(viewPage(data), validateURL(data) === null))
    }


    return <Field.Root validationMode="onChange" validate={validateURL} className="border border-gray-400 rounded-md py-2 mt-6 pr-2 pl-4">
        <Field.Description children={<>Navigate to a page:</>} />
        <Field.Error match='badInput' />
        <Field.Validity children={({ error, errors }) => (
            error ?  <> {errors.map((e, i) => <p key={i}>{e}</p>)} </> : null
        )} />
        <br />
        <Field.Label className="mr-4" children="Paste or type a URL" />
        <Field.Control className="border border-gray-400 rounded-md" onKeyDown={onKeyDown} onPasteCapture={onPaste} />
    </Field.Root>
}
export default UrlInput

function contains(dataset: Record<string, unknown>, value: string): boolean {
    return value in dataset
}

const redirect = (to: string, whenTrue: boolean) => {
    if (!whenTrue || !to) return null
    window.location.href = to.toString()
}

function viewPage(value: string): string {
    return `/page?q=${value}`
}