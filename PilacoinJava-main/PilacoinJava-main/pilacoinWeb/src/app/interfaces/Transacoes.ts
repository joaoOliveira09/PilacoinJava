export interface Transacoes {
    id?: number
    chaveUsuarioOrigem?: string
    chaveUsuarioDestino?: string
    assinatura?: string
    noncePila?: string
    dataTransacao?: Date
    status?: string  
}