export interface Transferencia {
    id?: number
    chaveUsuarioOrigem?:BinaryData
    chaveUsuarioDestino?:BinaryData
    assinatura?: string
    noncePila?: string
    dataTransacao?: Date
    status?: string
}